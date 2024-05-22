package macaroni.ui;

import macaroni.app.App;
import macaroni.app.Window;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.util.function.Function;

public class UiTests {
    protected FrameFixture window;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    protected static <T extends Component> GenericTypeMatcher<T> matchClass(Class<T> tClass) {
        return matchClass(tClass, t -> true);
    }

    protected static <T extends Component> GenericTypeMatcher<T> matchClass(Class<T> tClass, Function<T, Boolean> filter) {
        return new GenericTypeMatcher<>(tClass) {
            @Override
            protected boolean isMatching(T t) {
                return filter.apply(t);
            }
        };
    }

    @BeforeEach
    public void setUp() {
        Window wnd = GuiActionRunner.execute(() -> new App().getWindow());
        window = new FrameFixture(wnd);
        window.show();
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
