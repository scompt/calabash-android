package sh.calaba.instrumentationbackend.actions.simfy;

import android.widget.ScrollView;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

import java.util.ArrayList;

/**
 * Scrolls a scrollview to the top or bottom.
 *
 * args:
 * <ul>
 *   <li>either 'top' or 'bottom'</li>
 *   <li>1-based scrollview index (first scrollview used if not specified)</li>
 * </ul>
 */
public class ScrollScrollView implements Action {

	@Override
	public Result execute(String... args) {
		int scrollViewIndex;
        final int direction;

        if( args.length == 0) {
            return Result.failedResult("Must provide a direction (top, bottom)");
        } else if( args.length == 1 ) {
            direction = parseDirection(args[0]);
			scrollViewIndex = 0;
		} else {
            direction = parseDirection(args[0]);
			scrollViewIndex = (Integer.parseInt(args[1]) - 1);
		}

		ArrayList<ScrollView> scrollViews = InstrumentationBackend.solo.getCurrentScrollViews();
		if( scrollViews == null || scrollViews.size() <= scrollViewIndex ) {
			return new Result(false, "Could not find scrollview #" + (scrollViewIndex + 1));
		}

        final ScrollView list = scrollViews.get(scrollViewIndex);
        list.post(new Runnable() {
            @Override
            public void run() {
                list.fullScroll(direction);
            }
        });

        return Result.successResult();
	}

	@Override
	public String key() {
		return "scroll_scrollview";
	}

    private int parseDirection(String direction) {
        if ("top".equalsIgnoreCase(direction)) {
            return ScrollView.FOCUS_UP;
        } else if ("bottom".equalsIgnoreCase(direction)) {
            return ScrollView.FOCUS_DOWN;
        }
        throw new IllegalArgumentException("Unknown direction: " + direction);
    }
}