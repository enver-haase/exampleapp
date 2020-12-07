package org.vaadin.app.pageobjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.dialog.testbench.DialogElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import org.junit.Assert;

import java.util.NoSuchElementException;

public class CommentWindowElement extends DialogElement {

    /**
     * Enters a comment in the comment text field. Does not submit the comment.
     *
     * @param comment
     *            The text to enter in the comment field
     */
    public void enterComment(String comment) {
        getCommentInput().setValue(comment);
    }

    private TextFieldElement getCommentInput() {
        return $(VerticalLayoutElement.class).first().$(TextFieldElement.class).id("comment");
    }

    /**
     * Clicks the 'OK' button to submit the comment entered in the text field.
     */
    public void ok() {
        $(ButtonElement.class).id("ok").click();
    }

    /**
     * Clicks the 'Cancel' button to abort submitting the comment entered in the
     * text field.
     */
    public void cancel() {
        $(VerticalLayoutElement.class).first().$(ButtonElement.class).id("cancel").click();
    }

    @Override
    public boolean isOpen(){
        boolean open;
        try{
            open = super.isOpen();
        }
        // see https://github.com/vaadin/vaadin-flow-components/issues/500
        // this code should keep working even when above gets fixed eventually
        catch (org.openqa.selenium.NoSuchElementException ignored){
            open = false;
        }
        return open;
    }
}
