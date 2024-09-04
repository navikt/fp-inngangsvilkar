package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.util.Set;

public record MedlemskapsvilkårMellomregning(MedlemInngangsvilkårRegelGrunnlag grunnlag, Set<MedlemskapAvvik> avvik) {

    public void addAvvik(MedlemskapAvvik nyttAvvik) {
        avvik.add(nyttAvvik);
    }
}
