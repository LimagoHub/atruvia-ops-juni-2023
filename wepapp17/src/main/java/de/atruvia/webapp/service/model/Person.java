package de.atruvia.webapp.service.model;

import lombok.*;




@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private String id;
    private String vorname;
    private String nachname;
}
