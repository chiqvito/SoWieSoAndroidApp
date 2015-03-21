package pl.chiqvito.sowieso.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class Boast {

    private volatile static Boast globalBoast = null;

    private Toast internalToast;

    private Boast(Toast toast) {
        if (toast == null) {
            throw new NullPointerException("Boast.Boast(Toast) requires a non-null parameter.");
        }
        internalToast = toast;
    }

    @SuppressLint("ShowToast")
    public static Boast makeText(Context context, CharSequence text, int duration) {
        return new Boast(buildToast(context, text, duration));
    }

    @SuppressLint("ShowToast")
    public static Boast makeText(Context context, CharSequence text) {
        return new Boast(buildToast(context, text, Toast.LENGTH_SHORT));
    }

    public static void showText(Context context, CharSequence text, int duration) {
        Boast.makeText(context, text, duration).show();
    }

    public static void showText(Context context, CharSequence text) {
        Boast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private static Toast buildToast(Context ctx, CharSequence msg, int duration) {
        return Toast.makeText(ctx, msg, duration);
    }

    public void cancel() {
        internalToast.cancel();
    }

    public void show() {
        show(true);
    }

    public void show(boolean cancelCurrent) {
        // cancel current
        if (cancelCurrent && (globalBoast != null)) {
            globalBoast.cancel();
        }

        // save an instance of this current notification
        globalBoast = this;

        internalToast.show();
    }

}
