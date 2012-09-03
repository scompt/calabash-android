package sh.calaba.instrumentationbackend.actions.simfy;


import android.view.View;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;


/*
 * Warning: Unlike most other actions this action will allow you to click on views that are not visible.
 */
public class LongPressOnViewByContentDescription implements Action {

    @Override
    public Result execute(String... args) {
        final View view = TestHelpers.getViewByDescription(args[0]);

        if(view == null) {
            return new Result(false, "Could not find view with id: '" + args[0] + "'");
        }
        InstrumentationBackend.solo.clickLongOnView(view);
        return Result.successResult();
    }

    @Override
    public String key() {
        return "long_press_on_view_by_content_description";
    }
}
