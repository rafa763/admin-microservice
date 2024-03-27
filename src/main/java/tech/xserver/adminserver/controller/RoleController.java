package tech.xserver.adminserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xserver.adminserver.DTO.RoleDto;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.mappers.RoleMapper;
import tech.xserver.adminserver.service.RoleService;


@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping()
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @PostMapping()
    public ResponseEntity<?> addRole(@RequestBody RoleDto roleDto) {
        RoleEntity roleEntity = roleMapper.mapToRoleEntity(roleDto);
        return ResponseEntity.ok(roleService.addRole(roleEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id,@RequestBody RoleDto roleDto) {
        RoleEntity roleEntity = roleMapper.mapToRoleEntity(roleDto);
        return ResponseEntity.ok(roleService.updateRole(id, roleEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted");
    }
}
