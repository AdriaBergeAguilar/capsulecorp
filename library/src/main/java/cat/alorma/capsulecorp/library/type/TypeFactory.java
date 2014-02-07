package cat.alorma.capsulecorp.library.type;

import android.graphics.Rect;
import android.view.View;

import cat.alorma.capsulecorp.library.viewhelpers.Centers;

/**
 * Created by Bernat on 7/02/14.
 */
public class TypeFactory {
    private static int paddingLeft;
    private static int paddingRight;
    private static int paddingTop;
    private static int paddingBottom;
    private static Rect clipBounds;
    private static Centers centers;

    public static Type newInstance(View view, int size, int dividerSize) {
        Type type = createType(size);
        calculateClipBounds(view);
        calculatePaddings(view);
        calculateCenters(dividerSize);

        return setData(view, type, dividerSize);
    }

    public static Type setData(View view, Type type, int dividerSize) {
        calculateClipBounds(view);
        calculatePaddings(view);
        calculateCenters(dividerSize);

        type.setClipBounds(clipBounds);

        type.setCenters(centers);

        return type;
    }

    private static void calculateClipBounds(View view) {
        clipBounds = new Rect(0, 0, view.getWidth(), view.getHeight());
    }

    private static void calculatePaddings(View view) {
        paddingLeft = view.getPaddingLeft() < clipBounds.width() / 2 ? view.getPaddingLeft() : 0;
        paddingRight = view.getPaddingRight() < clipBounds.width() / 2 ? view.getPaddingRight() : 0;
        paddingTop = view.getPaddingTop() < clipBounds.height() / 2 ? view.getPaddingTop() : 0;
        paddingBottom = view.getPaddingBottom() < clipBounds.height() / 2 ? view.getPaddingBottom() : 0;

        clipBounds.left = paddingLeft;
        clipBounds.right = clipBounds.right - paddingRight;
        clipBounds.top = paddingTop;
        clipBounds.bottom = clipBounds.bottom - paddingBottom;
    }

    private static Type createType(int size) {
        Type type = new TypeOne();
        switch (size) {
            case 2:
                type = new TypeTwo();
                break;
            case 3:
                type = new TypeThree();
                break;
            case 4:
                type = new TypeFour();
                break;
        }
        return type;
    }

    private static void calculateCenters(int dividerSize) {
        int centerX = (clipBounds.width() / 2) + paddingLeft;
        int centerY = (clipBounds.height() / 2) + paddingTop;

        int centerXL = centerX;
        int centerXR = centerX;
        int centerYT = centerY;
        int centerYB = centerY;

        if (dividerSize != -1) {
            centerXL = centerXL - (dividerSize / 2);
            centerXR = centerXR + (dividerSize / 2);
            centerYT = centerYT - (dividerSize / 2);
            centerYB = centerYB + (dividerSize / 2);
        }

        centers = new Centers();
        centers.setCenterXL(centerXL);
        centers.setCenterXR(centerXR);
        centers.setCenterYT(centerYT);
        centers.setCenterYB(centerYB);
    }
}
