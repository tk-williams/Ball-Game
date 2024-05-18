package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uco.twilliams84.termprojecttimothyw.R;

public class GameFragment extends Fragment {
    private GameView gameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        gameView = (GameView) view.findViewById(R.id.view_game);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        gameView.setGameViewOpen(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
