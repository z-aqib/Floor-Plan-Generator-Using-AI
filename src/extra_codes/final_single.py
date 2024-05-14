import csv
import sys
import itertools


class TreeNode:
    def __init__(self, room):
        self.room = room
        self.children = []


def add_room_to_tree(root, room):
    new_node = TreeNode(room)
    root.children.append(new_node)
    return new_node


def build_room_design(root, room_design):
    room_design.append(root.room)
    for child in root.children:
        build_room_design(child, room_design)
    return room_design


def check_constraints(root, constraints):
    def dfs(node, parent_room):
        if parent_room:
            current_room = node.room
            for constraint in constraints:
                room1, room2 = constraint
                if (room1 == current_room and room2 == parent_room) or (room2 == current_room and room1 == parent_room):
                    return False
        for child in node.children:
            if not dfs(child, node.room):
                return False
        return True

    return dfs(root, None)


def rearrange_rooms(root, constraints):
    room_design = []
    build_room_design(root, room_design)

    room_permutations = itertools.permutations(room_design)
    for permutation in room_permutations:
        new_root = TreeNode(permutation[0])
        current_node = new_root
        for room in permutation[1:]:
            current_node = add_room_to_tree(current_node, room)
        if check_constraints(new_root, constraints):
            return permutation

            # If no valid arrangement is found
    return None


def main(rd):
    constraints = [
        ("kitchen", "master bedroom with bathroom"),
        ("dirty kitchen", "master bedroom with bathroom"),
        ("store", "master bedroom with bathroom"),
        ("bathroom", "master bedroom with bathroom"),
        ("kitchen", "guest room with bathroom"),
        ("dirty kitchen", "guest room with bathroom"),
        ("store", "guest room with bathroom"),
        ("bathroom", "guest room with bathroom")
    ]

    user_selection = {}

    total_area = int(rd[0])

    room_sizes = {}
    for i in range(1, len(rd)):
        room_info = rd[i].split(", ")  # Split the string into room and size
        room = room_info[0]
        size = room_info[1]
        room_sizes[room] = size
    root = None
    current_node = root

    total_selected_area = 0  # Variable to track the total selected area

    for room_info in rd[1:]:
        room, size = room_info.split(", ")  # Split the string into room and size
        if root is None:
            root = TreeNode(room)
            current_node = root
        else:
            current_node = add_room_to_tree(current_node, room)
        user_selection[room] = size
        total_selected_area += int(size)  # Add the size of the current room to the total selected area

    if total_selected_area > total_area:  # Check if total selected area exceeds the total area of the house
        print("The total area of the selected rooms exceeds the total area specified for the house.")
        return

    room_design = rearrange_rooms(root, constraints)
    if room_design:
        # if total_area - total_selected_area > 0:
            # print(f"You have {total_area - total_selected_area} square units left to fill.")
        return room_design
    else:
        print("Constraints violated. Unable to generate valid room design.")
        return []

# rd = [
#     "2800",
#     "master bedroom with bathroom, 700",
#     "store, 100",
#     "kitchen, 200",
#     "lounge area, 1200",
#     "balcony, 400",
#     "kitchen, 200",
# ]

# main(rd)