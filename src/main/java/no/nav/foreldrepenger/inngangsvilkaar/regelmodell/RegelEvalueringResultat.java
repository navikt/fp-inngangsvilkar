package no.nav.foreldrepenger.inngangsvilkaar.regelmodell;
public record RegelEvalueringResultat(String regelVersjon, RegelUtfall utfall, MerknadRuleReasonRef merknad,
                                      String regelEvaluering, String regelInput, Object resultatData) {
}
