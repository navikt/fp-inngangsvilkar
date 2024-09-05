package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.util.Set;

import no.nav.fpsak.tidsserie.LocalDateInterval;

public record Personopplysninger(Set<RegionPeriode> regioner, Set<LocalDateInterval> oppholdstillatelser, Set<PersonstatusPeriode> personstatus,
                                 Set<Adresse> adresser) {

    public record RegionPeriode(LocalDateInterval periode, Region region) {
    }

    public enum Region {
        NORDEN,
        EØS,
        TREDJELAND
    }

    public record PersonstatusPeriode(LocalDateInterval interval, Type type) {
        public enum Type {
            D_NUMMER,
            BOSATT_ETTER_FOLKEREGISTERLOVEN,
            IKKE_BOSATT,
            DØD,
            FORSVUNNET,
            OPPHØRT
        }
    }

    public record Adresse(LocalDateInterval periode, Type type, boolean erUtenlandsk) {

        public enum Type {
            BOSTEDSADRESSE,
            KONTAKTADRESSE,
            KONTAKTADRESSE_UTLAND,
            OPPHOLDSADRESSE_NORGE,
            OPPHOLDSADRESSE_UTLAND,
            UKJENT_ADRESSE,
        }
    }
}
