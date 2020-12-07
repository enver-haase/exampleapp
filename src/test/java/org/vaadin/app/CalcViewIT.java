package org.vaadin.app;

import org.junit.Assert;
import org.junit.Test;
import org.vaadin.app.pageobjects.CommentWindowElement;
import org.vaadin.app.pageobjects.ExampleAppTabsElement;
import org.vaadin.app.pageobjects.KeypadElement;
import org.vaadin.app.pageobjects.LogElement;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * A simple test case using page objects.
 */
public class CalcViewIT extends AbstractIT {

    private KeypadElement keypad;
    private LogElement log;

    @Override
    public void setUp() {
        super.setUp();

        $(ExampleAppTabsElement.class).first().click("Calculator");

        keypad = $(KeypadElement.class).first();
        log = $(LogElement.class).first();
    }

    @Test
    public void calculateOnePlusTwo() throws Exception {
        assertEquals("3.0", keypad.calculate("1+2"));
    }

    @Test
    public void resultIsLogged() throws Exception {
        keypad.calculate("1+2");
        assertEquals("1.0 + 2.0 = 3.0", getLog().getRow(0));
    }

    @Test
    public void testCalculateWithLongNumbers() throws Exception {
        assertEquals("1337.0", keypad.calculate("1337*5/5"));
        assertEquals("-4958.0", keypad.calculate("42-5000"));
    }

    @Test
    public void addCommentRowToLog() throws Exception {
        // Add a comment
        CommentWindowElement commentWindow = log.openCommentWindow();
        String comment = "That was a simple calculation";
        commentWindow.enterComment(comment);
        commentWindow.ok();

        // Ensure the comment window is closed and the log row was added
        assertFalse(commentWindow.isOpen());
        Assert.assertEquals("[ " + comment + " ]", getLog().getRow(0));
    }

    @Test
    public void addCommentButCancel() throws Exception {
        keypad.calculate("1+1");
        Assert.assertEquals("1.0 + 1.0 = 2.0", getLog().getRow(0));

        CommentWindowElement commentWindow = log.openCommentWindow();
        commentWindow.enterComment("1-2-3, does not end up anywhere");
        assertTrue(commentWindow.isOpen());
        commentWindow.cancel();

        // Ensure the comment window is closed and the log row was not added
        //assertFalse(commentWindow.isOpen());
        Assert.assertThrows(org.openqa.selenium.NoSuchElementException.class, commentWindow::isOpen);

        Assert.assertEquals("1.0 + 1.0 = 2.0", getLog().getRow(0));
    }

    private LogElement getLog() {
        return $(LogElement.class).id("log");
    }
}
