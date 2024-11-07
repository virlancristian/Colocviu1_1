package ro.pub.cs.systems.eim.Colocviu1_1;

import android.view.View;
import android.widget.TextView;

public class CoordinateButtonEventListener implements View.OnClickListener{
    private String coordinate;
    private Colocviu1_1MainActivity activity;

    public CoordinateButtonEventListener(String coordinate, Colocviu1_1MainActivity activity) {
        this.coordinate = coordinate;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) activity.findViewById(R.id.textView2);
        String textViewText = textView.getText().toString();

        textView.setText(textViewText.concat(", ".concat(coordinate)));
        this.activity.click();
    }
}
