package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.Personopplysninger.PersonstatusPeriode.Type.BOSATT_ETTER_FOLKEREGISTERLOVEN;
import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.Personopplysninger.PersonstatusPeriode.Type.D_NUMMER;
import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.Personopplysninger.PersonstatusPeriode.Type.DØD;

import java.util.List;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(SjekkOmBosattPersonstatus.ID)
class SjekkOmBosattPersonstatus extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    // Fra PDL-dokumentasjon om folkreregisterpersonstatus;
    // "Siden statusen er sterkt knyttet til Folkeregisterets lovverk og saksbehandling,
    // anbefaler vi PDLs konsumenter å være svært varsomme med å bruke denne opplysningstypen.
    // Folkeregisterpersonstatus bør dermed ikke benyttes for å avgjøre vilkår"
    // Ser derfor på identtype når det kommer til dnummer

    private static final List<Personopplysninger.PersonstatusPeriode.Type> GYLDIGE_STATUSER = List.of(BOSATT_ETTER_FOLKEREGISTERLOVEN, DØD,
        D_NUMMER);
    static final String ID = "FP_VK_2-3";

    private static final String BESKRIVELSE = "Har bruker bosatt personstatus?";

    SjekkOmBosattPersonstatus() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var grunnlag = mellomregning.grunnlag();
        var personopplysninger = grunnlag.personopplysninger();
        var vurderingsperiode = grunnlag.vurderingsperiodeBosatt();
        if (!MedlemFellesRegler.sjekkOmBosattPersonstatus(GYLDIGE_STATUSER, personopplysninger, vurderingsperiode) || personopplysninger.personIdentType() == Personopplysninger.PersonIdentType.DNR) {
            mellomregning.addAvvik(MedlemskapAvvik.BOSATT_UGYLDIG_PERSONSTATUS);
        }
        return ja();
    }

}
