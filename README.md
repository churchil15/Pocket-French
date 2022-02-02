# Pocket-French
A simple Android Application which can be used by students to learn basic French pronunciations by just a few clicks.

I started preparing this App when I was learning about navigation from one activity to another in android. 
Basically this was the first app in which I started using the concept of explicit intent. Usually in basic android
applications when we have to add a functionality to a button we use android:onClick attribute where we declare the name of
the method that we are going to define in our main main JAVA (or Kotlin) logic. But here I started exploring about event click listeners.
These callback methods give more diverse options in terms of any kind of triggered input from user and how to respond it more efficiently and 
easily.

The next important thing is to arrange the list of family members, colors, numbers and phrases. A useful way can be to use an Array of Strings to store
each and every kind of word but arrays are not efficients when it comes to changing the size of it dynamically. Hence the better alternative would be to use
ArrayList which can store in it the objects of a certain class (Primitive or user defined) as elements of the List. We have to make a class that contains
default and french translations as Strings and that class objects can be stored inside our ArrayList as elements. This gives us to store more complex 
information about one element in our ArrayList plus the option of dynamically changing the size of the list as our need.
To display the list of items present in our ArrayList, we need an adapter. Adapter converts an Array/ArrayList into view items. Basically they communicates 
with the data source (ArrayList in our case) and asks for each element and displays them onto the list view according to the instructions that we 
programmers provide them. The instructions to inflate the list item views and to also tell what to display inside the list items. Basically all the instructions
about how a single list item view is going to look is being stored inside the adapter.

The next thing is to enhance is the user experience of our app. This includes adding images associated with each word or an element inside the List.
This requires modifying our word class so that it can also store the image resource id of each and every particular element of the list. Thus displaying that image 
accordingly with that word and in order to so we modify the instructions given in adapter and also modify the list item view associated with it. Also to add 
different background colors list items present in different activities (Family, Colors, Numbers and Phrases). All these things makes our app more visually polished
and a good looking experience for the user.

Now comes the most important feature of this app which is the ability to pronunce the words and phrases by a single click of the user i.e., adding audio functionlity.
First, I used VoiceMaker (https://voicemaker.in/) in order to download the sounds of various words and phrases in french. VoiceMaker allows you to customize your 
sounds by providing the support of a number of languages along with the various voices to choose from. You can also adjust the volume, speed and pitch of your sound
according to your needs. Then we need to add all these sound files inside a raw directory in the resource section of android. This helps us to accommadate everything 
related with the audio at one place.
Second, in order to pull the functionality of audio we need to use the MediaPlayer class. This class provides APIs for playing variety of Media types.
We can simply create an object, add a music resource id and play it. But for these we need to again modify our Word class in order to accommodate the raw
resource id's of the different audio files associated with each word element. Then we can provide audio files as well that are associated with our words to the ArrayList that 
now has the ability to also take the audio file of each word in it. 
Third, this app also ensures that the resources are used wisely and are made free when they are not being used. This is achieved by something called Async Callbacks.
This simply means that do something else while waiting for the event to happen. When the event happens, we'll get called back, and can jump into action.

The last important thing was to add audio focus in the app. The problem with audio is that two or more android apps can play audio to the same output stream. The same thing
can happen while playing two or more audio files within the same app. This can mix everything and can make an unpleasant experience to the user. In order to resolve these
issues we AudioManager.OnAudioFocusChangeListener interface and setup an listener that manages everything related with audio focus in the app. The main 4 important states
of audio focus that needs to managed are: AUDIOFOCUS_GAIN, AUDIOFOCUS_LOSS, AUDIOFOCUS_LOSS_TRANSIENT, and AUDIO_LOSS_TRANSIENT_CAN_DUCK. You can read about them on the 
official android documentation of google (https://developer.android.com/guide/topics/media-apps/audio-focus).
