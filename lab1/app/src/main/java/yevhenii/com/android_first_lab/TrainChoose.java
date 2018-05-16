package yevhenii.com.android_first_lab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class TrainChoose extends AppCompatActivity {

    // UI references.
    private TextView mFromView;
    private TextView mToView;
    private RadioButton mAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_choose);

        mFromView = findViewById(R.id.from);
        mToView = findViewById(R.id.till);
        mAM = (RadioButton) findViewById(R.id.radioButton);
        mAM.setChecked(true);

        Button mEmailSignInButton = (Button) findViewById(R.id.find);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showInput();
            }
        });
    }

    private void showInput() {

        // Store values at the time of the login attempt.
        String from = mFromView.getText().toString();
        String to = mToView.getText().toString();
        String daypart = mAM.isChecked() ? "Morning" : "Evening";

        createDialog("Your train choose", String.format("From: %s\nTo: %s\nWhen: %s", from, to, daypart));
    }

    private void createDialog(String title, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }
}