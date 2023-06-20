package de.atruvia.webapp.demo;



import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Set;

@Named
public class Demo {



    private final Translator translator;






    @Autowired
    public Demo(/*@Qualifier("upper")*/ final Translator translator) {
        this.translator = translator;



        System.out.println(translator.translate("Constructor von Demo"));
    }

//    public Translator getTranslator() {
//        return translator;
//    }
//
//    @Autowired
//    public void setTranslator(final Translator translator) {
//        this.translator = translator;
//    }

//    public Demo() {
//        System.out.println("Constructor von Demo");
//    }

    @PostConstruct
    private void init() {

        System.out.println(translator.translate("Postconstruct"));

    }
}
