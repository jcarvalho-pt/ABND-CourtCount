package net.inete.tgpsi.jcarvalho.abnd_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   /**
    * Vars to keep score and qty of faults for both teams.
    */
   int scoreHome = 0;
   int scoreAway = 0;
   int faultsHome = 0;
   int faultsAway = 0;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // exists a saved state?
      if (savedInstanceState != null) {
         // restore the values
         scoreHome = savedInstanceState.getInt("homeGoals");
         scoreAway = savedInstanceState.getInt("awayGoals");
         faultsHome = savedInstanceState.getInt("homeFaults");
         faultsAway = savedInstanceState.getInt("awayFaults");
         String msg = savedInstanceState.getString("messages");
         int visible = savedInstanceState.getInt("visibility");
         // show the restored values and set views colors
         // messages
         TextView txtMsg = (TextView) findViewById(R.id.messages);
         txtMsg.setText(msg);
         // 2nd half reset
         Button btn2ndHalf = (Button) findViewById(R.id.reset_2nd_half);
         btn2ndHalf.setVisibility(visible);
         // scores
         updateScore(scoreHome, 'H');
         updateScore(scoreAway, 'A');
         setScoreColor();
         // faults
         if (faultsHome >= 5) {  // mas 5 faults per half, , but msg show the actual count
            updateFaults(5, 'H');
            setFaultColor('H');
         }
         else {
            updateFaults(faultsHome, 'H');
         }
         if (faultsAway >= 5) {  // mas 5 faults per half, but msg show the actual count
            updateFaults(5, 'A');
            setFaultColor('A');
         }
         else {
            updateFaults(faultsAway, 'A');
         }
      }
   }

   @Override
   protected void onSaveInstanceState(Bundle savedState) {
      /* save the current values */
      savedState.putInt("homeGoals", scoreHome);
      savedState.putInt("awayGoals", scoreAway);
      savedState.putInt("homeFaults", faultsHome);
      savedState.putInt("awayFaults", faultsAway);
      TextView txtMsg = (TextView) findViewById(R.id.messages);
      savedState.putString("messages", txtMsg.getText().toString());
      Button btn2ndHalf = (Button) findViewById(R.id.reset_2nd_half);
      int visible = btn2ndHalf.getVisibility();
      savedState.putInt("visibility", visible);

      super.onSaveInstanceState(savedState);
   }

   /**
    * Event handler click of button Goal Home.
    * Increase score and update view.
    */
   public void btnGoalHomeOnClick(View view) {
      scoreHome++;
      updateScore(scoreHome, 'H');
   }

   /**
    * Event handler click of button Away Home.
    * Increase score and update view
    */
   public void btnGoalAwayOnClick(View view) {
      scoreAway++;
      updateScore(scoreAway, 'A');
   }

   /**
    * Event handler click of button Fault Home.
    * Increase faults and update view.
    * If greater than 5, change the button color and show a msg.
    */
   public void btnFaultHomeOnClick(View view) {
      faultsHome++;
      if (faultsHome <= 5) {
         updateFaults(faultsHome, 'H');
      }
      else {
         TextView txtMsg = (TextView) findViewById(R.id.messages);
         String str = txtMsg.getText().toString();
         txtMsg.setText(getResources().getText(R.string.kick_away) + " (" +
               faultsHome + " " + getResources().getText(R.string.fault) + ")\n" + str);
      }
   }

   /**
    * Event handler click of button Fault Away.
    * Increase faults and update view.
    * If greater than 5, change the button color and show a msg.
    */
   public void btnFaultAwayOnClick(View view) {
      faultsAway++;
      if (faultsAway <= 5) {
         updateFaults(faultsAway, 'A');
      }
      else {
         TextView txtMsg = (TextView) findViewById(R.id.messages);
         String str = txtMsg.getText().toString();
         txtMsg.setText(getResources().getText(R.string.kick_home) + " (" +
               faultsAway + " " + getResources().getText(R.string.fault) + ")\n" + str);
      }
   }

   /**
    * Event handler click of button Reset.
    * Turn all views to the initial state.
    */
   public void btnResetOnClick(View view) {
      scoreHome = 0;
      scoreAway = 0;
      faultsHome = 0;
      faultsAway = 0;
      updateScore(scoreHome, 'H');
      updateScore(scoreAway, 'A');
      updateFaults(faultsHome, 'H');
      updateFaults(faultsAway, 'A');
      TextView txtMsg = (TextView) findViewById(R.id.messages);
      txtMsg.setText(getResources().getText(R.string.str_description));
      Button btnFault = (Button) findViewById(R.id.fault_away_team);
      btnFault.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
      btnFault = (Button) findViewById(R.id.fault_home_team);
      btnFault.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
      btnFault = (Button) findViewById(R.id.reset_2nd_half);
      btnFault.setVisibility(View.VISIBLE);
   }

   /**
    * Event handler click of button 2nd Half.
    * Turn the views associated to Faults to the initial state.
    * Hides the button (just one action allowed per match).
    */
   public void btn2ndHalfOnClick(View view) {
      faultsHome = 0;
      faultsAway = 0;
      updateFaults(faultsHome, 'H');
      updateFaults(faultsAway, 'A');
      Button btnFault = (Button) findViewById(R.id.fault_away_team);
      btnFault.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
      btnFault = (Button) findViewById(R.id.fault_home_team);
      btnFault.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
      btnFault = (Button) findViewById(R.id.reset_2nd_half);
      btnFault.setVisibility(View.INVISIBLE);
   }

   /**
    * Method to update the text and color of the score counters.
    */
   private void updateScore(int score, char team) {
      TextView txtScore;

      if (team == 'H') {
         txtScore = (TextView) findViewById(R.id.score_home_team);
      }
      else {
         txtScore = (TextView) findViewById(R.id.score_away_team);
      }
      txtScore.setText(String.valueOf(score));
      setScoreColor();
   }

   /**
    * Method to update the score counter's text the fault button's color.
    */
   private void updateFaults(int faults, char team) {
      TextView txtScore;

      if (team == 'H') {
         txtScore = (TextView) findViewById(R.id.faults_home_team);
      }
      else {
         txtScore = (TextView) findViewById(R.id.faults_away_team);
      }
      txtScore.setText(String.valueOf(faults));
      setFaultColor(team);
   }

   /**
    * Method to set the color of the fault buttons.
    */
   private void setFaultColor(char team) {
      Button btnScore;

      if (team == 'H') {
         if (faultsHome >= 5) {
            btnScore = (Button) findViewById(R.id.fault_home_team);
            btnScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
         }
      }
      else {
         if (faultsAway >= 5) {
            btnScore = (Button) findViewById(R.id.fault_away_team);
            btnScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
         }
      }
   }

   /**
    * Method to set the color of the scores numbers.
    */
   private void setScoreColor() {
      TextView txtScore;

      if (scoreHome > scoreAway) {
         txtScore = (TextView) findViewById(R.id.score_home_team);
         txtScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
         txtScore = (TextView) findViewById(R.id.score_away_team);
         txtScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
      }
      else {
         if (scoreAway > scoreHome) {
            txtScore = (TextView) findViewById(R.id.score_home_team);
            txtScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            txtScore = (TextView) findViewById(R.id.score_away_team);
            txtScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
         }
         else {
            txtScore = (TextView) findViewById(R.id.score_home_team);
            txtScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            txtScore = (TextView) findViewById(R.id.score_away_team);
            txtScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
         }
      }
   }
}