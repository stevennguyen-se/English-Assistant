package com.example.thesis;

import java.util.ArrayList;
import java.util.Locale;
import com.example.thesis.R;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import edu.sfsu.cs.orange.ocr.CaptureActivity;
import android.os.Bundle;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ExtraFunctionsFragment extends Fragment implements OnClickListener, TextToSpeech.OnInitListener{

	private TextToSpeech textToSpeech;
	
	private static ImageView imgExtraFunctionsCamera;
	private static ImageView imgExtraFunctionsMic;
	private static ImageView imgExtraFunctionsSpeaker;
	
	private static EditText etExtraFunctions;
	
	private static TextView txtExtraFunctions;
	
	public ExtraFunctionsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_extra_functions,
				container, false); 
		
		// Initial
		textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), this);
		
		// Set ID
		imgExtraFunctionsCamera = (ImageView) rootView.findViewById(R.id.imgExtraFunctionsCamera);
		imgExtraFunctionsMic = (ImageView) rootView.findViewById(R.id.imgExtraFunctionsMic);
		imgExtraFunctionsSpeaker = (ImageView) rootView.findViewById(R.id.imgExtraFunctionsSpeaker);
		
		etExtraFunctions = (EditText) rootView.findViewById(R.id.etExtraFunctions);
		
		txtExtraFunctions = (TextView) rootView.findViewById(R.id.txtExtraFunctions);
		txtExtraFunctions.setVisibility(View.VISIBLE);
		
		// Set on click listener
		imgExtraFunctionsCamera.setOnClickListener(this);
		imgExtraFunctionsMic.setOnClickListener(this);
		imgExtraFunctionsSpeaker.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgExtraFunctionsCamera:
			{
				Intent intent = new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(intent,
						GlobalNumber.REQUEST_CODE_HOME_CAPTURE_ACTIVITY);
			}
			break;
			case R.id.imgExtraFunctionsMic:
			{
				promptSpeechInput();
			}
			break;
			case R.id.imgExtraFunctionsSpeaker:
			{
				speakOut();
			}
			break;
			default:
				break;
		}
	}

	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, GlobalNumber.REQUEST_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GlobalNumber.REQUEST_CODE_HOME_CAPTURE_ACTIVITY) {
			if (resultCode == GlobalNumber.RESULT_OK) {
				// Write your code if there's a result

				Bundle bundle = data.getExtras();
				String resultText = bundle.getString(GlobalString.CAPTURE_TEXT);

				txtExtraFunctions.setVisibility(View.GONE);
				etExtraFunctions.setVisibility(View.VISIBLE);
				etExtraFunctions.setText(resultText);
			}
			if (resultCode == GlobalNumber.RESULT_CANCEL) {
				// Write your code if there's no result
			}
		}
		if (requestCode == GlobalNumber.REQUEST_CODE_SPEECH_INPUT) {

			if (null != data) {
				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				
				txtExtraFunctions.setVisibility(View.GONE);
				etExtraFunctions.setVisibility(View.VISIBLE);
				etExtraFunctions.setText(result.get(0));
			}
		}
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = textToSpeech.setLanguage(Locale.US);

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				imgExtraFunctionsSpeaker.setEnabled(true);
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		String mWord = etExtraFunctions.getText().toString();

		if (mWord.contains("/")) {
			int end = mWord.indexOf("/");
			mWord = mWord.substring(0, end);
		}
		
		textToSpeech.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override
	public void onDestroy() {

		// Don't forget to shutdown!
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
		super.onDestroy();
	}
}
