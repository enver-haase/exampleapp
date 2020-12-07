package org.vaadin.app.pageobjects;

import com.vaadin.flow.component.tabs.testbench.TabElement;
import com.vaadin.flow.component.tabs.testbench.TabsElement;
import com.vaadin.testbench.annotations.Attribute;

/**
 * This is a PageObject for the left-hand side Tabs of the ExampleApp application.
 * Functionality for manipulating / using said menu goes here, such as retrieving or clicking a particular Tab.
 */
@Attribute(name = "role", value = "tablist")
public class ExampleAppTabsElement extends TabsElement {

  public void click(String text) {
    get(text).$("a").first().click();
  }

  public TabElement get(String text){
    int i = getTab(text);
    return $(TabElement.class).get(i);
  }
}
