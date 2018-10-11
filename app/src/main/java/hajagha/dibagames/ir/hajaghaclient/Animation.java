package hajagha.dibagames.ir.hajaghaclient;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * Created by Pars on 4/29/2016.
 */
public class Animation {
    public static void RightMenuAnimationOpen(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "pivotX", 360);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "rotationY", 180, 0);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(object, "rotationX", 0, 360);
        animator3.setDuration(1000);
        animator3.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
    }

    public static void RightMenuAnimationClose(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "pivotX", 360);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "rotationY", 0, 180);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(object, "rotationX", 360, 0);
        animator3.setDuration(1000);
        animator3.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
    }

    public static void RightMenuAnimationOpen2(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "scaleX", 0, 1);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "scaleY", 0, 1);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2);
        animatorSet.start();
    }

    public static void RightMenuAnimationClose2(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "scaleX", 1, 0);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "scaleY", 1, 0);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2);
        animatorSet.start();
    }

    public static void RightMenuAnimationOpen3(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "pivotX", 360);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "pivotY", -100);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(object, "rotationY", 180, 0);
        animator3.setDuration(1000);
        animator3.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
    }

    public static void RightMenuAnimationClose3(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "pivotX", 360);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "pivotY", -100);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(object, "rotationY", 0, 180);
        animator3.setDuration(1000);
        animator3.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
    }
    public static void RightMenuAnimationOpen4(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "pivotX", 360);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "pivotY", 0);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(object, "rotationY", 180, 0);
        animator3.setDuration(1000);
        animator3.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
    }

    public static void RightMenuAnimationClose4(Object object, int delay) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(object, "pivotX", 360);
        animator1.setDuration(500);
        animator1.setStartDelay(delay);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(object, "pivotY", 0);
        animator2.setDuration(500);
        animator2.setStartDelay(delay);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(object, "rotationY", 0, 180);
        animator3.setDuration(1000);
        animator3.setStartDelay(delay);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
    }
}
