package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.util.Set;

import no.nav.fpsak.nare.RuleService;
import no.nav.fpsak.nare.Ruleset;
import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.Specification;


@RuleDocumentation(value = Medlemskapsvilkår.ID, specificationReference = "https://confluence.adeo.no/display/TVF/Automatisk+medlemskapsvurdering")
public class Medlemskapsvilkår implements RuleService<MedlemInngangsvilkårRegelGrunnlag> {

    public static final String ID = "FP_VK_2";

    @Override
    public Evaluation evaluer(MedlemInngangsvilkårRegelGrunnlag grunnlag, Object output) {
        var grunnlagOgMellomregning = new MedlemskapsvilkårMellomregning(grunnlag, (Set<MedlemskapAvvik>) output);
        var evaluated = getSpecification().evaluate(grunnlagOgMellomregning);
        evaluated.setEvaluationProperty("avvik", grunnlagOgMellomregning.avvik());
        return evaluated;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Specification<MedlemskapsvilkårMellomregning> getSpecification() {
        var rs = new Ruleset<MedlemskapsvilkårMellomregning>();

        return rs.sekvensRegel()
            .neste(new SjekkOmMedlRegisterPerioder())
            .neste(new SjekkOmOppgittUtenlandsopphold())
            .neste(new SjekkOmUtenlandsadresser())
            .neste(new SjekkOmOppholdstillatelserIHeleTredjelandsPerioden())
            .neste(new SjekkOmAnsettelseOgInntektIHeleEøsPerioden())
            .neste(new SjekkOmGyldigBostedadresse())
            .neste(new SjekkOmBosattPersonstatus())
            .siste(new MedlemskapAvvikSammenstilling());
    }
}
