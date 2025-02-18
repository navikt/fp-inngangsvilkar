package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.Personopplysninger.PersonIdentType;
import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.Personopplysninger.PersonstatusPeriode;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import no.nav.fpsak.tidsserie.LocalDateInterval;

class SjekkOmBosattPersonstatusTest {

    @Test
    void bosatt_status_med_dnr_personident_gir_avvik() {
        var mellomregning = grunnlag(PersonstatusPeriode.Type.BOSATT_ETTER_FOLKEREGISTERLOVEN, PersonIdentType.DNR);

        new SjekkOmBosattPersonstatus().evaluate(mellomregning);

        assertThat(mellomregning.avvik()).containsExactly(MedlemskapAvvik.BOSATT_UGYLDIG_PERSONSTATUS);
    }

    @Test
    void bosatt_status_med_fnr_personident_gir_ikke_avvik() {
        var mellomregning = grunnlag(PersonstatusPeriode.Type.BOSATT_ETTER_FOLKEREGISTERLOVEN, PersonIdentType.FNR);

        new SjekkOmBosattPersonstatus().evaluate(mellomregning);

        assertThat(mellomregning.avvik()).isEmpty();
    }

    @Test
    void ikke_bosatt_status_med_fnr_personident_gir_avvik() {
        var mellomregning = grunnlag(PersonstatusPeriode.Type.IKKE_BOSATT, PersonIdentType.FNR);

        new SjekkOmBosattPersonstatus().evaluate(mellomregning);

        assertThat(mellomregning.avvik()).containsExactly(MedlemskapAvvik.BOSATT_UGYLDIG_PERSONSTATUS);
    }

    private static MedlemskapsvilkårMellomregning grunnlag(PersonstatusPeriode.Type personStatus, PersonIdentType personIdentType) {
        var personopplysninger = new Personopplysninger(Set.of(), Set.of(),
            Set.of(new PersonstatusPeriode(new LocalDateInterval(LocalDate.now(), LocalDate.now()), personStatus)), Set.of(), personIdentType);
        var vurderingsperiode = new LocalDateInterval(LocalDate.now(), LocalDate.now());
        var grunnlag = new MedlemskapsvilkårGrunnlag(vurderingsperiode, null, null, personopplysninger, null, null, null, null, null);
        return new MedlemskapsvilkårMellomregning(grunnlag, new HashSet<>());
    }
}
