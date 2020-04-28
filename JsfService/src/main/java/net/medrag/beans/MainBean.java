package net.medrag.beans;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named("Main")
@ViewScoped
@Getter
@Setter
public class MainBean implements Serializable {

    private List<String> cargoes;
    private List<Integer> stats;
    private String textValue = "default";
    private String textValue2 = "";

    public void submitButt1() {
        System.out.println(textValue);
        System.out.println(textValue2);
        addMessage(textValue);
    }

    public void ajaxListener() {
        System.out.println("Firing ajax request...");
        System.out.println(textValue);
        System.out.println(textValue2);
        System.out.println("ok");
    }

    //  Does not work currently
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary);
        FacesContext.getCurrentInstance().addMessage("Title", message);
    }

    @PostConstruct
    public void init() {
        System.out.println("init phase");
        cargoes = Arrays.asList("one", "two", "three");
        stats = Arrays.asList(5, 15, 20, 25, 30, 35);
    }
}