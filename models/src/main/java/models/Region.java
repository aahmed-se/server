package models;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Maxime on 02/12/2015.
 */
@Embedded
public enum Region {
    br,
    eune,
    euw,
    kr,
    lan,
    las,
    na,
    oce,
    tr,
    ru;
}
