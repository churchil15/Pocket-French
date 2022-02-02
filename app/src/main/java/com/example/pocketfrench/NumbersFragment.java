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

public class NumbersFragment extends Fragment {
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

    public NumbersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        Word word0 = new Word("one (feminine)", "une", R.drawable.number_one_feminine, R.raw.number_one_feminine);
        Word word1 = new Word("one (masculine)", "un", R.drawable.number_one_masculine, R.raw.number_one_masculine);
        Word word2 = new Word("two", "deux", R.drawable.number_two, R.raw.number_two);
        Word word3 = new Word("three", "trois", R.drawable.number_three, R.raw.number_three);
        Word word4 = new Word("four", "quatre", R.drawable.number_four, R.raw.number_four);
        Word word5 = new Word("five", "cinq", R.drawable.number_five, R.raw.number_five);
        Word word6 = new Word("six", "six",R.drawable.number_six, R.raw.number_six);
        Word word7 = new Word("seven", "sept", R.drawable.number_seven, R.raw.number_seven);
        Word word8 = new Word("eight", "huit", R.drawable.number_eight, R.raw.number_eight);
        Word word9 = new Word("nine (feminine)", "neuve", R.drawable.number_nine_feminine, R.raw.number_nine_feminine);
        Word word10 = new Word("nine (masculine)", "neuf", R.drawable.number_nine_masculine, R.raw.number_nine_masculine);
        Word word11 = new Word("ten", "dix", R.drawable.number_ten, R.raw.number_ten);

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
        words.add(word11);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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