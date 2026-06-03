# fp-inngangsvilkar

Business-rule library for FP/ES/SVP entry conditions (inngangsvilkår) in relation to Folketrygdloven 14-2, 14-5, 14-6, and 14-17

## Shared context

- Source of truth for shared domain, architecture, and conventions: `navikt/fp-context`
- Copilot Space: `navikt/TeamForeldrepenger`

## Repo-specific context

| Topic       | Details                                                                         |
|-------------|---------------------------------------------------------------------------------|
| Role        | Evaluates entry conditions such as fødsel, adopsjon, medlemskap, and opptjening |
| Consumers   | `fp-sak` module `inngangsvilkar`                                                |
| Tech stack  | Java SemVer library using `fp-nare` rule framework                              |

Stateless rule definition and evaluation of misc grunnlag input objects. Contains history of rules since 2019.

## Verification

- Verify behavior changes through `fp-sak` and relevant `navikt/fp-autotest` flows such as `fpsak` or `verdikjede`.
