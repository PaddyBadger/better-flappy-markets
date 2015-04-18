#!/usr/bin/env ruby

module Flapgorithm
  GRAVITY = 10 # m/(s**2)
  FLAP_VELOCITY = 5 # m/s
  LATERAL_SPEED = 1 # m/s

  def should_flap?(altitude, target_altitude, target_distance)
    expected_flap_altitude = altitude + altitude_change(FLAP_VELOCITY, target_arrival_time(target_distance))
    target_altitude > expected_flap_altitude
  end

  def altitude_change(initial_velocity, time_elapsed)
    (initial_velocity * time_elapsed) + (-0.5 * GRAVITY * (time_elapsed ** 2))
  end

  def target_arrival_time(target_distance)
    target_distance / LATERAL_SPEED.to_f
  end

  module_function :altitude_change, :target_arrival_time, :should_flap?
end
