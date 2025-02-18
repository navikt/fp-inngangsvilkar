package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import static java.time.LocalDate.of;
import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.Personopplysninger.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.InngangsvilkårRegler;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelUtfallMerknad;
import no.nav.fpsak.tidsserie.LocalDateInterval;

class MedlemskapsvilkårTest {

    @Test
    void finner_avvik() {
        var vurderingsperiodeBosatt = new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 1));
        var vurderingsperiodeLovligOpphold = new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 2));
        var registrertMedlemskapPerioder = Set.of(new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 1)));
        var utenlandsadresse = new Adresse(new LocalDateInterval(vurderingsperiodeBosatt.getFomDato().minusYears(10), null), Adresse.Type.KONTAKTADRESSE_UTLAND,
            true);
        var region1 = new RegionPeriode(new LocalDateInterval(vurderingsperiodeLovligOpphold.getFomDato(), vurderingsperiodeLovligOpphold.getFomDato()),
            Region.TREDJELAND);
        var region2 = new RegionPeriode(vurderingsperiodeLovligOpphold, Region.EØS);
        var personopplysninger = new Personopplysninger(Set.of(region1, region2), Set.of(), Set.of(), Set.of(utenlandsadresse), PersonIdentType.FNR);
        var søknad = new MedlemskapsvilkårGrunnlag.Søknad(Set.of(new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 1))));
        var arbeid = new MedlemskapsvilkårGrunnlag.Arbeid(Set.of(), Set.of());
        var skjæringstidspunkt = of(2024, 1, 1);
        var behandlingsdato = of(2024, 1, 1);
        var grunnbeløp = new MedlemskapsvilkårGrunnlag.Beløp(BigDecimal.valueOf(1000));
        var grunnlag = new MedlemskapsvilkårGrunnlag(vurderingsperiodeBosatt, vurderingsperiodeLovligOpphold, registrertMedlemskapPerioder,
            personopplysninger, søknad, arbeid, skjæringstidspunkt, behandlingsdato, grunnbeløp);
        var regelEvalueringResultat = InngangsvilkårRegler.medlemskap(grunnlag);

        assertThat(regelEvalueringResultat.merknad().regelUtfallMerknad()).isEqualTo(RegelUtfallMerknad.RVM_1025);
        var avvik = (HashSet<MedlemskapAvvik>) regelEvalueringResultat.resultatData();
        assertThat(avvik).containsExactlyInAnyOrder(MedlemskapAvvik.MEDL_PERIODER, MedlemskapAvvik.BOSATT_UTENLANDSOPPHOLD, MedlemskapAvvik.BOSATT_UTENLANDSADRESSE,
            MedlemskapAvvik.TREDJELAND_MANGLENDE_LOVLIG_OPPHOLD, MedlemskapAvvik.EØS_MANGLENDE_ANSETTELSE_MED_INNTEKT, MedlemskapAvvik.BOSATT_MANGLENDE_BOSTEDSADRESSE, MedlemskapAvvik.BOSATT_UGYLDIG_PERSONSTATUS);
    }

    @Test
    void ingen_avvik_hvis_alt_ok() {
        var vurderingsperiodeBosatt = new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 1));
        var vurderingsperiodeLovligOpphold = new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 3));
        var norden = new RegionPeriode(new LocalDateInterval(of(2024, 1, 1), of(2024, 1, 1)), Region.NORDEN);
        var tredjeland = new RegionPeriode(new LocalDateInterval(of(2024, 1, 2), of(2024, 1, 2)), Region.TREDJELAND);
        var eøs = new RegionPeriode(new LocalDateInterval(of(2024, 1, 3), of(2024, 1, 3)), Region.EØS);
        var oppholdstillatelser = Set.of(tredjeland.periode());
        var personstatus = Set.of(new PersonstatusPeriode(vurderingsperiodeBosatt, PersonstatusPeriode.Type.BOSATT_ETTER_FOLKEREGISTERLOVEN));
        var adresser = Set.of(new Adresse(vurderingsperiodeBosatt, Adresse.Type.BOSTEDSADRESSE, false));
        var personopplysninger = new Personopplysninger(Set.of(norden, tredjeland, eøs), oppholdstillatelser, personstatus, adresser, PersonIdentType.FNR);
        var søknad = new MedlemskapsvilkårGrunnlag.Søknad(Set.of());
        var grunnbeløp = new MedlemskapsvilkårGrunnlag.Beløp(BigDecimal.valueOf(1000));
        var skjæringstidspunkt = of(2024, 1, 1);
        var arbeid = new MedlemskapsvilkårGrunnlag.Arbeid(Set.of(vurderingsperiodeLovligOpphold),
            Set.of(new MedlemskapsvilkårGrunnlag.Arbeid.Inntekt(new LocalDateInterval(of(2023, 12, 1), of(2023, 12, 31)), grunnbeløp)));
        var behandlingsdato = of(2024, 1, 1);
        var grunnlag = new MedlemskapsvilkårGrunnlag(vurderingsperiodeBosatt, vurderingsperiodeLovligOpphold, Set.of(),
            personopplysninger, søknad, arbeid, skjæringstidspunkt, behandlingsdato, grunnbeløp);
        var regelEvalueringResultat = InngangsvilkårRegler.medlemskap(grunnlag);

        assertThat(regelEvalueringResultat.merknad()).isNull();
        var avvik = (HashSet<MedlemskapAvvik>) regelEvalueringResultat.resultatData();
        assertThat(avvik).isEmpty();
    }

}
