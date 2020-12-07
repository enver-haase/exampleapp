package org.vaadin.app.views.calculator;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.vaadin.app.views.main.MainView;

@Route(value = "calculator", layout = MainView.class)
@PageTitle("Calculator")
public class CalcView extends HorizontalLayout {

    public CalcView() {
        setId("calc-view");
        setSpacing(true);
        setHeight("300px");
        Log log = new Log();
        log.setId("log");
        Keypad keypad = new Keypad(log);
        add(keypad, log);
    }

}
