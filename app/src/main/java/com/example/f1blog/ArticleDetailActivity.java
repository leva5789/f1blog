package com.example.f1blog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDetailActivity extends AppCompatActivity {

    private ImageView imageViewArticle;
    private TextView textViewArticleTitle, textViewArticleContent;
    private RecyclerView recyclerViewComments;
    private EditText editTextComment;
    private Button buttonAddComment;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private String articleId; // Egyszerűsítésként a title-t használjuk ID-ként

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        imageViewArticle = findViewById(R.id.imageViewArticle);
        textViewArticleTitle = findViewById(R.id.textViewArticleTitle);
        textViewArticleContent = findViewById(R.id.textViewArticleContent);
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        editTextComment = findViewById(R.id.editTextComment);
        buttonAddComment = findViewById(R.id.buttonAddComment);

        // Cikk adatok betöltése
        String title = getIntent().getStringExtra("articleTitle");
        String content = getIntent().getStringExtra("articleContent");
        String image = getIntent().getStringExtra("articleImage");
        articleId = title; // Egyszerűsítés, később valódi ID-t használunk

        textViewArticleTitle.setText(title);
        textViewArticleContent.setText(content);
        // imageViewArticle.setImageResource(R.drawable.placeholder_image); // Később API-ból

        // Kommentek listája
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList, this);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(commentAdapter);

        loadComments();

        // Komment hozzáadása
        buttonAddComment.setOnClickListener(v -> {
            String commentText = editTextComment.getText().toString().trim();
            if (commentText.isEmpty()) {
                Toast.makeText(this, "Kérlek, írj kommentet!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mAuth.getCurrentUser() != null) {
                String username = mAuth.getCurrentUser().getEmail().split("@")[0]; // Egyszerűsítés
                addComment(commentText, username);
                editTextComment.setText("");
            } else {
                Toast.makeText(this, "Kérlek, jelentkezz be!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments() {
        db.collection("comments").document(articleId).collection("comments")
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
                        } else {
                            Toast.makeText(ArticleDetailActivity.this, "Hiba a kommentek betöltésekor: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                    loadComments(); // Frissítjük a listát
                    Toast.makeText(this, "Komment hozzáadva!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a komment hozzáadása közben: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void deleteComment(String commentId) {
        if (mAuth.getCurrentUser() != null) {
            String currentUserEmail = mAuth.getCurrentUser().getEmail().split("@")[0];
            db.collection("comments").document(articleId).collection("comments").document(commentId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String commentUsername = documentSnapshot.getString("username");
                            if (currentUserEmail.equals(commentUsername)) {
                                db.collection("comments").document(articleId).collection("comments").document(commentId)
                                        .delete()
                                        .addOnSuccessListener(aVoid -> {
                                            loadComments();
                                            Toast.makeText(this, "Komment törölve!", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "Hiba a törlés közben: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(this, "Csak a saját kommentedet törölheted!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void editComment(String commentId, String newText) {
        if (mAuth.getCurrentUser() != null) {
            String currentUserEmail = mAuth.getCurrentUser().getEmail().split("@")[0];
            db.collection("comments").document(articleId).collection("comments").document(commentId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String commentUsername = documentSnapshot.getString("username");
                            if (currentUserEmail.equals(commentUsername)) {
                                Map<String, Object> updates = new HashMap<>();
                                updates.put("text", newText);
                                db.collection("comments").document(articleId).collection("comments").document(commentId)
                                        .update(updates)
                                        .addOnSuccessListener(aVoid -> {
                                            loadComments();
                                            Toast.makeText(this, "Komment szerkesztve!", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "Hiba a szerkesztés közben: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(this, "Csak a saját kommentedet szerkesztheted!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}