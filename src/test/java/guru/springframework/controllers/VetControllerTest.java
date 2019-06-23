package guru.springframework.controllers;

import guru.springframework.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.controllers.VetController;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VetControllerTest {

    VetController vetController;

    @BeforeEach
    void setUp() {
        SpecialtyService specialtyService = new SpecialityMapService();
        VetService vetService = new VetMapService(specialtyService);
        vetController = new VetController(vetService);

        Speciality speciality = new Speciality("Making cookies");
        Speciality speciality2 = new Speciality("Making pies");
        Set<Speciality> specialitySet = new HashSet<>();
        specialitySet.add(speciality);
        specialitySet.add(speciality2);

        Vet vet = new Vet(1L, "jos", "lolman", specialitySet);
        Vet vet1 = new Vet(2L, "jef", "brolman", specialitySet);

        vetService.save(vet);
        vetService.save(vet1);
    }

    @Test
    void listVetsTest() {
        Model model = new ModelMapImpl();
        String view = vetController.listVets(model);
        Set modelAttribute = (Set) ((ModelMapImpl) model).getMap().get("vets");

        assertEquals("vets/index", view);
        assertEquals(2, modelAttribute.size());
    }
}
