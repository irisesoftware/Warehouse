package in.irise.soft.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.irise.soft.model.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {

}
