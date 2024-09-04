package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.util.Comparator;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.MerknadRuleReasonRef;
import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(MedlemskapAvvikSammenstilling.ID)
class MedlemskapAvvikSammenstilling extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-0";

    MedlemskapAvvikSammenstilling() {
        super(ID);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var funnetAvvik = mellomregning.avvik();
        return funnetAvvik.isEmpty() ? ja() : nei(utledReason(mellomregning));
    }

    private static MerknadRuleReasonRef utledReason(MedlemskapsvilkårMellomregning mellomregning) {
        var merknad = mellomregning.avvik().stream().min(Comparator.comparing(MedlemskapAvvik::getPrioritet)).orElseThrow().getRegelUtfallMerknad();
        return new MerknadRuleReasonRef(merknad, "Kan ikke automatisk avklares som medlem");
    }
}
