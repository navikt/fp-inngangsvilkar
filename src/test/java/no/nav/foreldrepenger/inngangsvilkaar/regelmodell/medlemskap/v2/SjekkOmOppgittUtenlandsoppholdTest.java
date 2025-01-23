package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import no.nav.fpsak.tidsserie.LocalDateInterval;

class SjekkOmOppgittUtenlandsoppholdTest {

    @Test
    void utenlandsopphold_og_ingen_revurderingÅrsak() {
        assertThat(SjekkOmOppgittUtenlandsopphold.harAvvik(Set.of(), null)).isFalse();
        assertThat(SjekkOmOppgittUtenlandsopphold.harAvvik(Set.of(new LocalDateInterval(LocalDate.now(), LocalDate.now())), null)).isTrue();
    }

    @Test
    void utenlandsopphold_og_revurderingÅrsak() {
        var utenlandsopphold = Set.of(new LocalDateInterval(LocalDate.now(), LocalDate.now()));
        assertThat(SjekkOmOppgittUtenlandsopphold.harAvvik(utenlandsopphold, RevurderingÅrsak.ANNEN)).isFalse();
        assertThat(SjekkOmOppgittUtenlandsopphold.harAvvik(utenlandsopphold, RevurderingÅrsak.MANUELL)).isTrue();
    }
}
