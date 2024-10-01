package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.VilkårGrunnlag;
import no.nav.fpsak.tidsserie.LocalDateInterval;

public record MedlemskapsvilkårGrunnlag(LocalDateInterval vurderingsperiodeBosatt, LocalDateInterval vurderingsperiodeLovligOpphold,
                                        Set<LocalDateInterval> registrertMedlemskapPerioder, Personopplysninger personopplysninger, Søknad søknad,
                                        Arbeid arbeid, LocalDate skjæringstidspunkt, LocalDate behandlingsdato,
                                        Beløp grunnbeløp) implements VilkårGrunnlag {

    public record Søknad(Set<LocalDateInterval> utenlandsopphold) {
    }

    public record Arbeid(Set<LocalDateInterval> ansettelsePerioder, Set<Inntekt> inntekter) {
        public record Inntekt(LocalDateInterval interval, Beløp beløp) {
        }
    }

    public record Beløp(BigDecimal value) implements Comparable<Beløp> {

        public static final Beløp ZERO = new Beløp(BigDecimal.ZERO);

        public Beløp add(Beløp b) {
            return new Beløp(value.add(b.value));
        }

        @Override
        public int compareTo(Beløp o) {
            return value.compareTo(o.value);
        }

        public Beløp divide(Beløp b, RoundingMode roundingMode) {
            return new Beløp(value.divide(b.value, roundingMode));
        }

        public boolean erMerEnn(Beløp beløp) {
            return value.compareTo(beløp.value) > 0;
        }
    }
}
