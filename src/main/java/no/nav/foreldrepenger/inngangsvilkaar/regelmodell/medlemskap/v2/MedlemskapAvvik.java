package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelUtfallMerknad;

public enum MedlemskapAvvik {
    BOSATT_UTENLANDSOPPHOLD(1, RegelUtfallMerknad.RVM_1025),
    BOSATT_MANGLENDE_BOSTEDSADRESSE(1, RegelUtfallMerknad.RVM_1025),
    BOSATT_UTENLANDSADRESSE(1, RegelUtfallMerknad.RVM_1025),
    BOSATT_UGYLDIG_PERSONSTATUS(1, RegelUtfallMerknad.RVM_1025),
    TREDJELAND_MANGLENDE_LOVLIG_OPPHOLD(2, RegelUtfallMerknad.RVM_1023),
    EØS_MANGLENDE_ANSETTELSE_MED_INNTEKT(3, RegelUtfallMerknad.RVM_1024),
    MEDL_PERIODER(4, RegelUtfallMerknad.RVM_1020);

    private final int prioritet;
    private final RegelUtfallMerknad regelUtfallMerknad;

    MedlemskapAvvik(int prioritet, RegelUtfallMerknad regelUtfallMerknad) {

        this.prioritet = prioritet;
        this.regelUtfallMerknad = regelUtfallMerknad;
    }

    public int getPrioritet() {
        return prioritet;
    }

    public RegelUtfallMerknad getRegelUtfallMerknad() {
        return regelUtfallMerknad;
    }
}
