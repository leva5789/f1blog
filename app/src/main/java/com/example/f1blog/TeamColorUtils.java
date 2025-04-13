package com.example.f1blog;

import androidx.annotation.ColorRes;

public class TeamColorUtils {

    public static @ColorRes int getTeamColor(String teamName) {
        if (teamName == null) {
            return R.color.default_team_color;
        }
        switch (teamName.toLowerCase()) {
            case "mercedes":
                return R.color.mercedes;
            case "red bull":
            case "red bull racing":
                return R.color.red_bull;
            case "ferrari":
                return R.color.ferrari;
            case "mclaren":
                return R.color.mclaren;
            case "alpine":
                return R.color.alpine;
            case "racing bulls":
                return R.color.racing_bulls;
            case "aston martin":
                return R.color.aston_martin;
            case "williams":
                return R.color.williams;
            case "kick sauber":
            case "sauber":
                return R.color.kick_sauber;
            case "haas":
                return R.color.haas;
            default:
                return R.color.default_team_color;
        }
    }
}