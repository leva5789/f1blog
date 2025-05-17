package com.example.f1blog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDetailActivity extends AppCompatActivity {

    private ImageView imageViewArticle, imageViewBack, imageViewAddComment;
    private TextView textViewArticleTitle, textViewArticleContent;
    private RecyclerView recyclerViewComments;
    private EditText editTextComment;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private String articleId;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        imageViewArticle = findViewById(R.id.imageViewArticle);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewAddComment = findViewById(R.id.imageViewAddComment);
        textViewArticleTitle = findViewById(R.id.textViewArticleTitle);
        textViewArticleContent = findViewById(R.id.textViewArticleContent);
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        editTextComment = findViewById(R.id.editTextComment);

        imageViewBack.setOnClickListener(v -> onBackPressed());

        String title = getIntent().getStringExtra("articleTitle");
        String content = getIntent().getStringExtra("articleContent");
        String image = getIntent().getStringExtra("articleImage");
        articleId = title;

        textViewArticleTitle.setText(title);
        textViewArticleContent.setText(content);

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList, this);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(commentAdapter);

        if (mAuth.getCurrentUser() != null) {
            String uid = mAuth.getCurrentUser().getUid();
            db.collection("users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            currentUsername = documentSnapshot.getString("username");
                            loadComments();
                        }
                    })
                    .addOnFailureListener(e -> {});
        } else {
            finish();
        }

        imageViewAddComment.setOnClickListener(v -> {
            String commentText = editTextComment.getText().toString().trim();
            if (commentText.isEmpty()) {
                return;
            }
            if (currentUsername != null) {
                addComment(commentText, currentUsername);
                editTextComment.setText("");
            }
        });
    }

    private void loadComments() {
        db.collection("comments").document(articleId).collection("comments")
                .orderBy("timestamp", Query.Direction.DESCENDING) // Kommentek rendezése timestamp szerint, legutóbbi legfelül
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            commentList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String text = document.getString("text");
                                String username = document.getString("username");
                                String id = document.getId();
                                commentList.add(new Comment(id, text, username));
                            }
                            commentAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void addComment(String text, String username) {
        Map<String, Object> comment = new HashMap<>();
        comment.put("text", text);
        comment.put("username", username);
        comment.put("timestamp", System.currentTimeMillis());

        db.collection("comments").document(articleId).collection("comments")
                .add(comment)
                .addOnSuccessListener(documentReference -> {
                    loadComments();
                })
                .addOnFailureListener(e -> {});
    }

    public void deleteComment(String commentId) {
        if (mAuth.getCurrentUser() != null && currentUsername != null) {
            db.collection("comments").document(articleId).collection("comments").document(commentId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String commentUsername = documentSnapshot.getString("username");
                            if (currentUsername.equals(commentUsername)) {
                                db.collection("comments").document(articleId).collection("comments").document(commentId)
                                        .delete()
                                        .addOnSuccessListener(aVoid -> {
                                            loadComments();
                                        })
                                        .addOnFailureListener(e -> {});
                            }
                        }
                    });
        }
    }

    public void editComment(String commentId, String newText) {
        if (mAuth.getCurrentUser() != null && currentUsername != null) {
            db.collection("comments").document(articleId).collection("comments").document(commentId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String commentUsername = documentSnapshot.getString("username");
                            if (currentUsername.equals(commentUsername)) {
                                Map<String, Object> updates = new HashMap<>();
                                updates.put("text", newText);
                                db.collection("comments").document(articleId).collection("comments").document(commentId)
                                        .update(updates)
                                        .addOnSuccessListener(aVoid -> {
                                            loadComments();
                                        })
                                        .addOnFailureListener(e -> {});
                            }
                        }
                    });
        }
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Valós idejű frissítés: a kommentek újratöltése, amikor az activity láthatóvá válik
        if (currentUsername != null) {
            loadComments();
        }
    }
}