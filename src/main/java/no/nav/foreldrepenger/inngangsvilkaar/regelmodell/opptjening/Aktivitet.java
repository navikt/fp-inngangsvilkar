package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening;

import java.util.Objects;
import java.util.Set;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening.fp.OpptjeningsvilkårForeldrepenger;

public record Aktivitet(String aktivitetType, String aktivitetReferanse, ReferanseType referanseType) {

    public enum ReferanseType {
        ORGNR,
        AKTØRID;
    }

    private static final Set<String> MED_REFERANSE = Set.of(OpptjeningsvilkårForeldrepenger.ARBEID,
        OpptjeningsvilkårForeldrepenger.FRILANSREGISTER, OpptjeningsvilkårForeldrepenger.LØNN);

    public Aktivitet {
        Objects.requireNonNull(aktivitetType, "aktivitetType må være satt");
        if (MED_REFERANSE.contains(aktivitetType)) {
            Objects.requireNonNull(aktivitetReferanse, "aktivitetReferanse må være satt");
            Objects.requireNonNull(referanseType, "referanseType må være satt");
        }
    }

    public Aktivitet forInntekt() {
        return new Aktivitet(OpptjeningsvilkårForeldrepenger.LØNN, aktivitetReferanse, referanseType);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "<type=" + aktivitetType
                + (aktivitetReferanse == null ? "" : ", referanse=" + aktivitetReferanse.replaceAll("^\\d{5}", "*****"))
                + (referanseType == null ? "" : ", referanseType=" + referanseType)
                + ">";

    }
}
