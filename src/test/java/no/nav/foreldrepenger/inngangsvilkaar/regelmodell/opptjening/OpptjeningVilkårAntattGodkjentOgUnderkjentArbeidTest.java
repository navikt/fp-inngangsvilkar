package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening.fp.OpptjeningsvilkårForeldrepenger;
import no.nav.fpsak.tidsserie.LocalDateInterval;
import no.nav.fpsak.tidsserie.LocalDateTimeline;

class OpptjeningVilkårAntattGodkjentOgUnderkjentArbeidTest {

    private static final String ARBEID = OpptjeningsvilkårForeldrepenger.ARBEID;

    private static final Aktivitet BIG_CORP =new Aktivitet(ARBEID, "BigCorp", Aktivitet.ReferanseType.ORGNR);
    private static final Aktivitet SMALL_CORP = new Aktivitet(ARBEID, "SmallCorp", Aktivitet.ReferanseType.ORGNR);
    private static final Aktivitet NO_CORP = new Aktivitet(ARBEID, "NoCorp", Aktivitet.ReferanseType.ORGNR);

    @Test
    void skal_beregne_underkjente_perioder_med_arbeid_ved_sammenligning_med_inntekt_grunnlag() {
        var dt1 = LocalDate.of(2017, Month.SEPTEMBER, 2);
        var dt2 = LocalDate.of(2017, Month.SEPTEMBER, 7);
        var dt3 = LocalDate.of(2017, Month.OCTOBER, 10);
        var dt4 = LocalDate.of(2017, Month.OCTOBER, 15);

        var o1 = LocalDate.of(2017, Month.SEPTEMBER, 3);
        var o2 = LocalDate.of(2017, Month.SEPTEMBER, 11);

        // unngå antatt godkjent
        var behandlingstidspunkt = LocalDate.of(2018, Month.JANUARY, 18);

        // arbeid aktivitet
        var aktiviteter = List.of(
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt1, dt2), BIG_CORP),
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt3, dt4), BIG_CORP),
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt2, dt4), NO_CORP)
        );
        // inntekt
        var inntekter = List.of(
            new InntektPeriode(new LocalDateInterval(dt3, dt4), BIG_CORP.forInntekt(), 1L),
            new InntektPeriode(new LocalDateInterval(o1, o2), BIG_CORP.forInntekt(), 0L)
        );

        var grunnlag = new Opptjeningsgrunnlag(behandlingstidspunkt, dt1, behandlingstidspunkt, aktiviteter, inntekter);

        var output = new OpptjeningsvilkårResultat();
        new OpptjeningsvilkårForeldrepenger().evaluer(grunnlag, output);

        assertThat(output.getAntattGodkjentePerioder()).isEmpty();

        assertThat(output.getUnderkjentePerioder())
                .containsEntry(BIG_CORP, new LocalDateTimeline<>(dt1, dt2, Boolean.TRUE))
                .containsEntry(NO_CORP, new LocalDateTimeline<>(dt2, dt4, Boolean.TRUE));

    }

    @Test
    void skal_beregne_antatt_godkjent_arbeid() {
        var dt1 = LocalDate.of(2017, Month.NOVEMBER, 2);
        var dt2 = LocalDate.of(2017, Month.NOVEMBER, 7);
        var dt3 = LocalDate.of(2017, Month.DECEMBER, 10);
        var dt4 = LocalDate.of(2017, Month.DECEMBER, 15);

        // matcher antatt godkjent kun for dt3-dt4
        var behandlingstidspunkt = LocalDate.of(2018, Month.JANUARY, 1);

        // skal også med som antatt selv om ingen inntekter er rapportert
        var førsteArbeidsdagSmallCorp = dt3.withDayOfMonth(1);
        var aktiviteter = List.of(
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt1, dt2), BIG_CORP),
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt3, dt4), BIG_CORP),
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(førsteArbeidsdagSmallCorp, dt4), SMALL_CORP)
        );
        // inntekt
        var inntekter = List.of(
            new InntektPeriode(new LocalDateInterval(dt1, dt3), BIG_CORP.forInntekt(), 1L)
        );

        var grunnlag = new Opptjeningsgrunnlag(behandlingstidspunkt, dt1, dt4, aktiviteter, inntekter);

        var output = new OpptjeningsvilkårResultat();
        new OpptjeningsvilkårForeldrepenger().evaluer(grunnlag, output);

        assertThat(output.getUnderkjentePerioder()).isEmpty();

        assertThat(output.getAntattGodkjentePerioder())
                .containsEntry(BIG_CORP,
                        new LocalDateTimeline<>(dt3.plusDays(1), dt4, Boolean.TRUE))
                .containsEntry(SMALL_CORP,
                        new LocalDateTimeline<>(førsteArbeidsdagSmallCorp, dt4, Boolean.TRUE));

    }

    @Test
    void skal_beregne_antatt_godkjent_over_underkjent_arbeid_der_de_overlapper() {
        var dt1 = LocalDate.of(2017, Month.OCTOBER, 2);
        var dt2 = LocalDate.of(2017, Month.OCTOBER, 7);
        var dt3 = LocalDate.of(2017, Month.DECEMBER, 10);
        var dt4 = LocalDate.of(2017, Month.DECEMBER, 15);

        // matcher antatt godkjent kun for dt3-dt4
        var behandlingstidspunkt = LocalDate.of(2018, Month.JANUARY, 18);

        // skal også med som antatt selv om ingen inntekter er rapportert
        var aktiviteter = List.of(
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt1, dt2), BIG_CORP),
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt3, dt4), BIG_CORP),
            AktivitetPeriode.periodeTilVurdering(new LocalDateInterval(dt2, dt4), SMALL_CORP)
        );

        var grunnlag = new Opptjeningsgrunnlag(behandlingstidspunkt, dt1, behandlingstidspunkt, aktiviteter, List.of());

        // Act
        var output = new OpptjeningsvilkårResultat();
        new OpptjeningsvilkårForeldrepenger().evaluer(grunnlag, output);

        var førsteAntattGodkjenteDag = behandlingstidspunkt.plusMonths(1).minus(Period.ofMonths(2)).withDayOfMonth(1);

        // Assert

        // sjekk underkjente perioder
        assertThat(output.getUnderkjentePerioder())
                .containsEntry(BIG_CORP, new LocalDateTimeline<>(dt1, dt2, Boolean.TRUE))
                .containsEntry(SMALL_CORP, new LocalDateTimeline<>(dt2, førsteAntattGodkjenteDag.minusDays(1), Boolean.TRUE))
                ;

        // sjekk antatt godkjente perioder
        assertThat(output.getAntattGodkjentePerioder())
                .containsEntry(BIG_CORP, new LocalDateTimeline<>(dt3, dt4, Boolean.TRUE))
                .containsEntry(SMALL_CORP,
                        new LocalDateTimeline<>(førsteAntattGodkjenteDag, dt4, Boolean.TRUE));

        // sjekk at antatt og underkjent arbeid aldri overlapper
        for (var entry : output.getUnderkjentePerioder().entrySet()) {
            var other = output.getAntattGodkjentePerioder().get(entry.getKey());
            assertThat(entry.getValue().intersects(other)).as("Skal ikke intersecte for " + entry.getKey()).isFalse();
        }

    }
}
