import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

public class PostBrainee {
    private static final String PACKAGE = "com.braineet.braineet";
    private Integer brandId;
    private Context context;

    public PostBrainee(final Context context, final Integer brandId) {
        this.brandId = brandId;
        this.context = context;
    }

    public void launch() {
        if (isPackageExisted()) {
            postBrainee();
        } else {
            goToPlayStore();
        }
    }

    private void postBrainee() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra("brandId", brandId);
        sendIntent.setType("text/plain");
        sendIntent.setPackage(PACKAGE);
        try {
            context.startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException activityNotFoundException) {
            Log.e("PostBrainee", "Error activity not found, please make sure you have the last Braineet app's version");
        }
    }

    private void goToPlayStore() {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PACKAGE)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + PACKAGE)));
        }
    }

    private boolean isPackageExisted(){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(PACKAGE,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}
