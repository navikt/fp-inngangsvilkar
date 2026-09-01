package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;

import no.nav.fpsak.tidsserie.LocalDateInterval;

public record InntektPeriode(LocalDateInterval datoIntervall, Aktivitet aktivitet, @JsonAlias("inntektBelop") Long inntektBeløp) {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof InntektPeriode other && Objects.equals(aktivitet, other.aktivitet)
                && Objects.equals(datoIntervall, other.datoIntervall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datoIntervall, aktivitet);
    }


}
