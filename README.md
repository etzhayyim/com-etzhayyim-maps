# com-etzhayyim-maps

Standalone Maps actor repository.

Canonical internal contracts and data are EDN under `manifest.edn`, `data/`,
`schema/`, and `kotoba.app.edn`. External interchange artifacts are isolated
under `wire/`: JSON lexicons and fixtures, JSON-LD actor metadata, and BPMN.

Runtime code lives in `src/maps/`; tests mirror it under `test/maps/`.
The retained Rust component is under `rust/` and is not a deprecated language twin.

Run `bb test` and `bb audit` before publishing.

The standalone suite excludes two retained cross-repository integration guards:
`test_bpmn_generic.cljc` needs jp-ashiba and govUSA-PA contracts, while
`test_maps3d_bpmn_fidelity.cljc` is owned by the separate maps3d application.
