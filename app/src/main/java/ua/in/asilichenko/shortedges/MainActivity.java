package ua.in.asilichenko.shortedges;

import static java.lang.Math.max;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setDecorFitsSystemWindows();
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout), this::onApplyWindowInsets);
  }

  /**
   * Hide program navigation bar: back, home, ... in the bottom of the screen
   * <a href="https://developer.android.com/develop/ui/views/layout/edge-to-edge">Display content edge-to-edge in your app</a>
   */
  private void setDecorFitsSystemWindows() {
    WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
  }

  /**
   * <a href="https://developer.android.com/develop/ui/views/layout/edge-to-edge">Display content edge-to-edge in your app</a>
   *
   * @see OnApplyWindowInsetsListener
   */
  @NonNull
  public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat windowInsets) {
    final Insets displayCutoutInsets = windowInsets.getInsets(WindowInsetsCompat.Type.displayCutout());
    final Insets systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
    final Insets safeInsets = Insets.of(
        max(displayCutoutInsets.left, systemBarsInsets.left),
        max(displayCutoutInsets.top, systemBarsInsets.top),
        max(displayCutoutInsets.right, systemBarsInsets.right),
        max(displayCutoutInsets.bottom, systemBarsInsets.bottom)
    );

    Log.d(getLocalClassName(), "");
    Log.d(getLocalClassName(), "displayCutoutInsets");
    Log.d(getLocalClassName(), "top=" + displayCutoutInsets.top);
    Log.d(getLocalClassName(), "left=" + displayCutoutInsets.left);
    Log.d(getLocalClassName(), "right=" + displayCutoutInsets.right);
    Log.d(getLocalClassName(), "bottom=" + displayCutoutInsets.bottom);

    Log.d(getLocalClassName(), "");
    Log.d(getLocalClassName(), "systemBarsInsets");
    Log.d(getLocalClassName(), "top=" + systemBarsInsets.top);
    Log.d(getLocalClassName(), "left=" + systemBarsInsets.left);
    Log.d(getLocalClassName(), "right=" + systemBarsInsets.right);
    Log.d(getLocalClassName(), "bottom=" + systemBarsInsets.bottom);

    Log.d(getLocalClassName(), "");
    Log.d(getLocalClassName(), "safeInsets");
    Log.d(getLocalClassName(), "top=" + safeInsets.top);
    Log.d(getLocalClassName(), "left=" + safeInsets.left);
    Log.d(getLocalClassName(), "right=" + safeInsets.right);
    Log.d(getLocalClassName(), "bottom=" + safeInsets.bottom);
    Log.d(getLocalClassName(), "");

    return WindowInsetsCompat.CONSUMED;
  }
}
