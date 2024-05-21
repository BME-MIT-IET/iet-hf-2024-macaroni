import macaroni.app.App;
import macaroni.app.Window;
import macaroni.app.menuView.*;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class UiTest {
    private FrameFixture window;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        Window wnd = GuiActionRunner.execute(() -> new App().getWindow());
        window = new FrameFixture(wnd);
        window.show();
    }

    @Test
    public void test() {
        var plumberPanel = window.panel(new GenericTypeMatcher<>(TeamPanel.class) {
            @Override
            protected boolean isMatching(TeamPanel jPanel) {
                return Arrays.stream(jPanel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Plumbers"));
            }
        });
        var elements = plumberPanel.list(new GenericTypeMatcher<>(MenuList.class) {
            @Override
            protected boolean isMatching(MenuList jList) {
                return true;
            }
        });
        var textBox = plumberPanel.textBox(new GenericTypeMatcher<>(TeamTextBox.class) {
            @Override
            protected boolean isMatching(TeamTextBox jTextComponent) {
                return true;
            }
        });
        var submitButton = plumberPanel.button(new GenericTypeMatcher<>(MenuTeamButton.class) {
            @Override
            protected boolean isMatching(MenuTeamButton jButton) {
                return true;
            }
        });
        elements.requireItemCount(0);
        textBox.enterText("slububu");
        submitButton.click();
        elements.requireItemCount(1);
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
