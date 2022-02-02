package com.example.pocketfrench;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    private void releaseMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private AudioManager audioManager;

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    public ColorsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        Word word0 = new Word("red", "rouge", R.drawable.color_red, R.raw.color_red);
        Word word1 = new Word("green (feminine)", "verte", R.drawable.color_green, R.raw.color_green_feminine);
        Word word2 = new Word("green (masculine)", "vert", R.drawable.color_green, R.raw.color_green_masculine);
        Word word3 = new Word("brown", "brun", R.drawable.color_brown, R.raw.color_brown);
        Word word4 = new Word("gray (feminine)", "grise", R.drawable.color_gray, R.raw.color_gray_feminine);
        Word word5 = new Word("gray (masculine)", "gris", R.drawable.color_gray, R.raw.color_gray_masculine);
        Word word6 = new Word("black", "noire", R.drawable.color_black, R.raw.color_black);
        Word word7 = new Word("white (feminine)", "blanche", R.drawable.color_white, R.raw.color_white_feminine);
        Word word8 = new Word("white (masculine)", "blanc", R.drawable.color_white, R.raw.color_white_masculine);
        Word word9 = new Word("dusty yellow", "jaune poussiéreux", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow);
        Word word10 = new Word("mustard yellow", "jaune moutarde", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow);

        words.add(word0);
        words.add(word1);
        words.add(word2);
        words.add(word3);
        words.add(word4);
        words.add(word5);
        words.add(word6);
        words.add(word7);
        words.add(word8);
        words.add(word9);
        words.add(word10);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Word word = words.get(position);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmSoundResourceID());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

        return rootView;
    }
}