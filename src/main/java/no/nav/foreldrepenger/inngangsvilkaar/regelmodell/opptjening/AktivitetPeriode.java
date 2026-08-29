package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening;

import java.util.Objects;

import no.nav.fpsak.tidsserie.LocalDateInterval;

/** Beskriver aktivitet for en angitt periode. */
public record AktivitetPeriode(LocalDateInterval datoIntervall,
                               Aktivitet aktivitet,
                               VurderingsStatus vurderingsStatus) implements Comparable<AktivitetPeriode> {


    @Override
    public int compareTo(AktivitetPeriode o) {
        return this.datoIntervall().compareTo(o.datoIntervall());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AktivitetPeriode other && Objects.equals(aktivitet(), other.aktivitet())
                && Objects.equals(datoIntervall(), other.datoIntervall());
    }

    @Override
    public int hashCode() {
        return Objects.hash(datoIntervall(), aktivitet());
    }

     public enum VurderingsStatus {
        TIL_VURDERING,
        VURDERT_GODKJENT,
        VURDERT_UNDERKJENT
    }

    public static AktivitetPeriode periodeTilVurdering(LocalDateInterval datoIntervall, Aktivitet aktivitet) {
        return new AktivitetPeriode(datoIntervall, aktivitet, AktivitetPeriode.VurderingsStatus.TIL_VURDERING);
    }
}
