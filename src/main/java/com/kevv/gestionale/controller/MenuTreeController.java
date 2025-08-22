package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.MenuTree;
import com.kevv.gestionale.model.MenuTreeId;
import com.kevv.gestionale.model.ApplMenu;
import com.kevv.gestionale.repository.MenuTreeRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Menu_tree")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MenuTreeController {

    private final MenuTreeRepository repository;

    // 🔹 Vedere i figli di un parent
    @GetMapping("/parent/{parentCode}")
    public List<MenuTree> getChildren(@PathVariable String parentCode) {
        List<MenuTree> children = repository.findById_Parent_Code(parentCode);
        children.forEach(c ->
                c.setFolder(repository.existsById_Parent_Code(c.getId().getApplmenu().getCode()))
        );
        return children;
    }

    // 🔹 Vedere un singolo menu
    @GetMapping("/{applMenuCode}")
    public MenuTree getMenu(@PathVariable String applMenuCode) {
        return repository.findByApplmenuCode(applMenuCode)
                .orElseThrow(() -> new RuntimeException("Menu non trovato"));
    }

    // 🔹 Modifica menu
    @PutMapping("/{applMenuCode}")
    public MenuTree updateMenu(@PathVariable String applMenuCode,
                               @RequestBody MenuTree updatedMenu) {
        MenuTree menu = repository.findByApplmenuCode(applMenuCode)
                .orElseThrow(() -> new RuntimeException("Menu non trovato"));

        menu.setRow(updatedMenu.getRow());
        menu.setCol(updatedMenu.getCol());
        menu.setMain(updatedMenu.isMain());

        // Se vuoi aggiornare anche la descrizione:
        // menu.getId().getApplmenu().setDescr(updatedMenu.getId().getApplmenu().getDescr());

        return repository.save(menu);
    }

    // 🔹 Aggiungi nuovo menu/cartella/file
    @PostMapping("/parent/{parentCode}")
    public MenuTree addMenu(@PathVariable String parentCode,
                            @RequestBody MenuTree newMenu) {

        // Imposta il parent corretto
        ApplMenu parent = new ApplMenu();
        parent.setCode(parentCode);
        newMenu.getId().setParent(parent);

        return repository.save(newMenu);
    }

    // 🔹 Cancella menu
    @DeleteMapping("/{applMenuCode}")
    public void deleteMenu(@PathVariable String applMenuCode) {
        repository.deleteById_Applmenu_Code(applMenuCode);
    }

    @PutMapping("/{parentCode}/update-max")
    public MenuTree updateParentMax(@PathVariable String parentCode,
                                    @RequestBody MaxRequest req) {
        MenuTree parent = repository.findByApplmenuCode(parentCode)
                .orElseThrow(() -> new RuntimeException("Parent non trovato"));

        if (req.getMaxRow() > 0) parent.getId().getParent().setMaxRow((short) req.getMaxRow());
        if (req.getMaxCol() > 0) parent.getId().getParent().setMaxCol((short) req.getMaxCol());

        return repository.save(parent);
    }

    @Data
    public static class MaxRequest {
        private int maxRow;
        private int maxCol;
    }

}