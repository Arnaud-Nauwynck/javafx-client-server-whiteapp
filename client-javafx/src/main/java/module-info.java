module fr.an.whiteapps.clisrvwhiteapp.clientjavafx {

    requires javafx.graphics;
    requires javafx.controls;
    requires retrofit2;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires okhttp3;
    requires retrofit2.converter.jackson;

    // special case for lombok... which is <scope>provided</scope> in maven !
    requires static lombok;

    // module 'clientjavafx' dependends on module 'common'
    requires fr.an.whiteapps.clisrvwhiteapp.common;

    // export for javafx!
    exports fr.an.whiteapps.clisrvwhiteapp.clientjavafx.ui;
}
