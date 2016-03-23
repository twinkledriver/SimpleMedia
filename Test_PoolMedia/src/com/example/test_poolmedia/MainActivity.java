package com.example.test_poolmedia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.HashMap;//引入HashMap类
import android.app.Activity;//引入Activity类
import android.content.Context;//引入Context类
import android.media.AudioManager;//引入AudioManager类
import android.media.MediaPlayer;//引入MediaPlayer类
import android.media.SoundPool;//引入SoundPool类
import android.os.Bundle;//引入Bundle类
import android.view.View;//引入View类
import android.view.View.OnClickListener;//引入OnClickListener类
import android.widget.Button;//引入Button类
import android.widget.TextView;//引入TextView类

public class MainActivity extends Activity implements OnClickListener {
	
	Button button1;//四个按钮的引用
    Button button2;
    Button button3;
    Button button4; 
    
    TextView textView;//TextView的引用
	MediaPlayer mMediaPlayer; 
	SoundPool soundPool;//声音
	HashMap<Integer, Integer> soundPoolMap; 
    

    @Override
    public void onCreate(Bundle savedInstanceState) {//重写onCreate回调方法
    	   super.onCreate(savedInstanceState);
           initSounds();//初始化声音
           setContentView(R.layout.activity_main);				//设置显示的用户界面
           
           textView = (TextView) this.findViewById(R.id.textView);//得到TextView的引用
           button1 = (Button) this.findViewById(R.id.button1);//得到button的引用
           button2 = (Button) this.findViewById(R.id.button2);
           button3 = (Button) this.findViewById(R.id.button3);
           button4 = (Button) this.findViewById(R.id.button4);
           button1.setOnClickListener(this);//为四个按钮添加监听
           button2.setOnClickListener(this);
           button3.setOnClickListener(this);
           button4.setOnClickListener(this);
    }
    public void initSounds(){//初始化声音的方法
    	mMediaPlayer = MediaPlayer.create(this, R.raw.backsound);//初始化MediaPlayer
	    soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100); //maxStream —— 同时播放的流的最大数量
	    															  //streamType —— 流的类型，一般为STREAM_MUSIC(具体在AudioManager类中列出)
	    															  //srcQuality —— 采样率转化质量，当前无效果，使用0作为默认值
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    soundPoolMap.put(1, soundPool.load(this, R.raw.dingdong, 1));
    }
    
    public void playSound(int sound, int loop) {//用SoundPoll播放声音的方法
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent/streamVolumeMax;   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);//该方法的第一个参数指定播放哪个声音；leftVolume、rightVolume指定左、右的音量：
	    																	 //priority指定播放声音的优先级，数值越大，优先级越高；loop指定是否循环，0为不循环，-1为循环；
	    																	 //rate指定播放的比率，数值可从0.5到2， 1为正常比率。
	}    
    
    public void onClick(View v) {//实现接口中的方法
    	if(v == button1){//点击了使用MediaPlayer播放声音按钮
			textView.setText("使用MediaPlayer播放声音");
			if(!mMediaPlayer.isPlaying()){
				mMediaPlayer.start();//播放声音
			}
		}
    	else if(v == button2){//点击了暂停MediaPlayer声音按钮
			textView.setText("暂停了MediaPlayer播放的声音");
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();//暂停声音
			}
		}
		else if(v == button3){//点击了使用SoundPool播放声音按钮
			textView.setText("使用SoundPool播放声音");
			this.playSound(1, 0);
		}
		else if(v == button4){//点击了暂停SoundPool声音按钮
			textView.setText("暂停了SoundPool播放的声音");
			soundPool.pause(1);//暂停SoundPool的声音
		}		
    	
    }
   
    
}
