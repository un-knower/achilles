create or replace view v_sj_order_had_payment as  
    select * from (
    select 
        `o`.order_num,
        `o`.client_id,
        `o`.user_id ,
        `o`.money,
        `o`.`city_id`,
        to_char(FROM_UNIXTIME(cast(`o`.`pay_time` as bigint)),'yyyy-mm-dd hh:mi:ss')  as pay_at
    from 
        `qc_16860_order` `o`
    where o.order_state in (35,36)
    union all
    select 
        `o`.order_num,
        `o`.client_id,
        `o`.user_id ,
        `o`.actual_cost as money,
        `o`.`city_id`,
        `o`.`paid_time`as pay_at
    from 
        `qc_takeaway_order` `o`
    where `o`.`status` in (2002,2030)
union all
    select 
        `o`.order_num,
        `o`.client_id ,
        `o`.user_id ,
        to_char(`o`.`money`)  AS `money`,
        `o`.`city_id`,
        `o`.update_time as pay_at
    from
        `qc_16860_offstaff_order` `o`
    where `o`.`status`=20) payment_orders ;