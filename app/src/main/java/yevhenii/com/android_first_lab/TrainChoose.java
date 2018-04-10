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
    private TextView mTillView;
    private RadioButton mAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_choose);

        mFromView = findViewById(R.id.from);
        mTillView = findViewById(R.id.till);
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
        String till = mTillView.getText().toString();
        String daypart = mAM.isChecked() ? "a.m." : "p.m.";

        try {
            int fr = Integer.valueOf(from);
            int tl = Integer.valueOf(till);

            if (tl >= fr && tl >= 0 && fr >= 0 && tl <= 12 && fr <= 12) {
                createDialog("Your input", String.format("From: %s(%s)\nTill: %s(%s)", from, daypart, till, daypart));
            } else {
                createDialog("Error!", "Bad input");
            }

        } catch (Exception e) {
            createDialog("Error!", "Bad input");
        }
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