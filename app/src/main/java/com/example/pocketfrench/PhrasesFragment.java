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

public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        Word word0 = new Word("Have a nice meal", "Bon appétit", R.raw.phrases_meal);
        Word word1 = new Word("Where are you going?", "Où allez-vous?", R.raw.phrase_where_are_you_going);
        Word word2 = new Word("What is your name?", "Quel est ton nom?", R.raw.phrase_what_is_your_name);
        Word word3 = new Word("My name is...", "Mon nom est...", R.raw.phrase_my_name_is);
        Word word4 = new Word("How are you feeling?", "Comment allez-vous?", R.raw.phrase_how_are_you_feeling);
        Word word5 = new Word("I’m feeling good.", "Je me sens bien.", R.raw.phrase_im_feeling_good);
        Word word6 = new Word("Are you coming?", "Viens-tu?", R.raw.phrase_are_you_coming);
        Word word7 = new Word("Yes, I’m coming.", "Oui, je viens.", R.raw.phrase_yes_im_coming);
        Word word8 = new Word("I’m coming.", "J'arrive.", R.raw.phrase_im_coming);
        Word word9 = new Word("Let’s go.", "Allons-y.", R.raw.phrase_lets_go);
        Word word10 = new Word("Come here.", "Viens ici.", R.raw.phrase_come_here);

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

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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
